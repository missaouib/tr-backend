package com.main.app.repository.counter_slug;

import com.main.app.domain.model.counter_slug.CounterSlug;
import com.main.app.domain.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterSlugRepository extends JpaRepository<CounterSlug, Long> {

    CounterSlug findByEntityName(String entityName);

}
