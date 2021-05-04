
package com.main.app.util;

import com.main.app.domain.model.product.Product;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;

public class Slug {
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String makeSlug(String slugToMake) {

        if(slugToMake == null || slugToMake == "")
            throw new IllegalArgumentException();

        String nowhitespace = WHITESPACE.matcher(slugToMake).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}