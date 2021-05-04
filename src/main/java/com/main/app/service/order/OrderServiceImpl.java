package com.main.app.service.order;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.order.OrderDto;
import com.main.app.domain.model.order.CustomerOrder;
import com.main.app.domain.model.order_item.OrderItem;
import com.main.app.domain.model.product.Product;
import com.main.app.domain.model.shopping_cart.ShoppingCart;
import com.main.app.domain.model.shopping_cart_item.ShoppingCartItem;
import com.main.app.domain.model.variation.Variation;
import com.main.app.elastic.dto.order.OrdersElasticDTO;
import com.main.app.elastic.repository.order.OrderElasticRepository;
import com.main.app.elastic.repository.order.OrderElasticRepositoryBuilder;
import com.main.app.repository.order.OrderItemRepository;
import com.main.app.repository.order.OrderRepository;
import com.main.app.repository.product.ProductRepository;
import com.main.app.repository.shopping_cart.ShoppingCartRepository;
import com.main.app.repository.shopping_cart_item.ShoppingCartItemRepository;
import com.main.app.repository.variation.VariationRepository;
import com.main.app.service.order_item.OrderItemService;
import com.main.app.service.shopping_cart.ShoppingCartService;
import com.main.app.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static com.main.app.converter.order.OrderConverter.*;
import static com.main.app.static_data.Messages.*;
import static com.main.app.util.Util.dtoOrdersToIds;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {

    private final ShoppingCartService shoppingCartService;

    private final ShoppingCartRepository shoppingCartRepository;

    private final OrderElasticRepository orderElasticRepository;

    private final OrderElasticRepositoryBuilder orderElasticRepositoryBuilder;

    private final OrderItemService orderItemService;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final ShoppingCartItemRepository shoppingCartItemRepository;

    private final ProductRepository productRepository;

    private final VariationRepository variationRepository;

    @Override
    public CustomerOrder createOrder(OrderDto orderDto) {
        ShoppingCart shoppingCart = shoppingCartService.findShoppingCartById(orderDto.getShoppingCartId());
        if(shoppingCart.getItems().size() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, SHOPPING_CART_IS_EMPTY);
        }
        CustomerOrder order = orderRepository.save(toEntity(orderDto));
        order.setOrderItems(new ArrayList<OrderItem>());

        //TODO Provjera da li je current user,shopping cart user?
        order.setUser(shoppingCart.getUser());

        for(ShoppingCartItem shoppingCartItem: shoppingCart.getItems()){
            OrderItem orderItem = orderItemService.create(shoppingCartItem);
            order.getOrderItems().add(orderItem);
            orderItem.setCustomerOrder(order);
            orderItemRepository.save(orderItem);
            shoppingCartItem.setDeleted(true);
            shoppingCartItemRepository.save(shoppingCartItem);
        }

        int total = 0;
        for (OrderItem item: order.getOrderItems()) {

            Product product = productRepository.findById(item.getProduct().getId()).get();
            Integer quantity = Integer.valueOf(product.getAvailable()) - Integer.valueOf(item.getQuantity());
            product.setAvailable(quantity);
            productRepository.save(product);

            if(item.getVariation() != null){
                Variation variation = variationRepository.findById(item.getVariation().getId()).get();
                Integer kolicina = Integer.valueOf(variation.getAvailable()) - Integer.valueOf(item.getQuantity());
                variation.setAvailable(kolicina);
                variationRepository.save(variation);
            }


            if(item.getVariation() != null){
                total += item.getQuantity() * item.getVariation().getPrice();
            }
            else{
                 if(item.getProduct() != null){
                     total += item.getQuantity() * item.getProduct().getPrice();
                 }
            }
        }

        order.setTotalPrice(total);
        order.setStatus("Poruceno.Na Cekanju");

        shoppingCart.setDeleted(true);
        shoppingCartRepository.save(shoppingCart);

        CustomerOrder orderSaved = orderRepository.save(order);
        orderElasticRepository.save(new OrdersElasticDTO(orderSaved));

        return orderSaved;
    }

    @Override
    public Entities getAllBySearchParam(String searchParam, Pageable pageable, String startDate, String endDate, String startPrice, String endPrice) {


            Page<OrdersElasticDTO> pageOrders = orderElasticRepository.search(orderElasticRepositoryBuilder.generateQuery(searchParam,startDate,endDate,startPrice,endPrice), pageable);

            List<Long> ids = dtoOrdersToIds(pageOrders);

            Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
            List<CustomerOrder> orders = orderRepository.findAllByIdInAndDeletedFalse(ids, mySqlPaging).getContent();

            List<OrderDto> ordersDto = ordersListToOrdersDTOList(orders);


            Entities entities = new Entities();
            entities.setEntities(ordersDto);
            entities.setTotal(pageOrders.getTotalElements());

            return entities;
    }

    @Override
    public CustomerOrder getOne(Long id) {
        return orderRepository.getOne(id);
    }

    @Override
    public CustomerOrder removeOrderItem(Long id, Long itemId) {

        CustomerOrder customerOrder = orderRepository.findById(id).get();
        OrderItem orderItem = orderItemRepository.findById(itemId).get();

        Product product = productRepository.findById(orderItem.getProduct().getId()).get();
        int quantity = Integer.valueOf(product.getAvailable()) + Integer.valueOf(orderItem.getQuantity());
        product.setAvailable(quantity);
        productRepository.save(product);

        if(orderItem.getVariation() != null){
            Variation variation = variationRepository.findById(orderItem.getVariation().getId()).get();
            Integer kolicina = Integer.valueOf(variation.getAvailable()) + Integer.valueOf(orderItem.getQuantity());
            variation.setAvailable(kolicina);
            variationRepository.save(variation);
        }

        customerOrder.getOrderItems().remove(orderItem);
        orderItemService.removeItemById(itemId);

        int total = 0;
        for (OrderItem item: customerOrder.getOrderItems()) {

            if(item.getVariation() != null){
                total += item.getQuantity() * item.getVariation().getPrice();
            }else{
                if(item.getProduct() != null){
                    total += item.getQuantity() * item.getProduct().getPrice();
                }
           }
        }
        

        customerOrder.setTotalPrice(total);

        CustomerOrder savedOrder = orderRepository.save(customerOrder);

        orderElasticRepository.save(new OrdersElasticDTO(savedOrder));

        return savedOrder;

    }

    @Override
    public CustomerOrder changeStatusOfOrder(Long id) {

      CustomerOrder customerOrder = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ORDER_NOT_EXIST));
      if(customerOrder.getStatus().equals("Poslato")){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ORDER_STATUS_IS_FINISHED);
      }else{
        customerOrder.setStatus("Poslato");
      }
      CustomerOrder savedCustomOrder = orderRepository.save(customerOrder);

      orderElasticRepository.save(new OrdersElasticDTO(savedCustomOrder));


        return savedCustomOrder;
    }

    @Override
    public CustomerOrder removeOrder(Long id) {

        Optional<CustomerOrder> customerOrder = orderRepository.findById(id);

        if(!customerOrder.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ORDER_NOT_EXIST);
        }

        List<OrderItem> orderItems = orderItemRepository.findAllByCustomerOrderId(id);
        for (int i=0;i<orderItems.size();i++) {
            orderItemService.removeItemById(orderItems.get(i).getId());
        }

        CustomerOrder order = customerOrder.get();
        order.setDeleted(true);
        order.setDateDeleted(Calendar.getInstance().toInstant());

        CustomerOrder savedOrder = orderRepository.save(order);
        orderElasticRepository.save(new OrdersElasticDTO(order));

        return savedOrder;
    }
}
