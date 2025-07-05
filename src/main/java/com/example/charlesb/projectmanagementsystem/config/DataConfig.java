package com.example.charlesb.projectmanagementsystem.config;

import com.example.charlesb.projectmanagementsystem.dao.RoleRepository;
import com.example.charlesb.projectmanagementsystem.entity.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataConfig {

    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
        return runner -> {
            createDefaultRoles(roleRepository);
        };
    }

    private void createDefaultRoles(RoleRepository repo) {
        if (repo.count() == 0) {
            Role user = new Role();
            Role manager = new Role();
            Role admin = new Role();
            Role owner = new Role();

            user.setName("ROLE_USER");
            manager.setName("ROLE_MANAGER");
            admin.setName("ROLE_ADMIN");
            owner.setName("ROLE_OWNER");

            repo.save(user);
            repo.save(manager);
            repo.save(admin);
            repo.save(owner);
        }
    }

}
