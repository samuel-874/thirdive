package com.jme.shareride.service.categoryServices;

import com.jme.shareride.entity.transport.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
@EnableJpaRepositories
public interface CategoryRepository extends JpaRepository<Category,Long>, PagingAndSortingRepository<Category,Long> {
    Category findByCategoryName(String name);
}
