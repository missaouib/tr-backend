package com.main.app.config.auditor_aware;

import com.main.app.config.SecurityUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return SecurityUtils.getCurrentUserLogin();
    }
}
