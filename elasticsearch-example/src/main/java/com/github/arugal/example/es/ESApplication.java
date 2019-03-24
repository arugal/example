package com.github.arugal.example.es;

import com.github.arugal.example.es.dao.RepositorieDao;
import com.github.arugal.example.es.entity.Repositorie;
import com.github.arugal.example.es.esdao.RepositorieESDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * @author: zhangwei
 * @date: 13:45/2019-03-14
 */
@SpringBootApplication
public class ESApplication implements CommandLineRunner {

    @Autowired
    private RepositorieDao repositorieDao;

    @Autowired
    private RepositorieESDao repositorieESDao;

    public static void main(String[] args) {
        SpringApplication.run(ESApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        int start_row = 0, record = 0, batch_size = 200;
        do {
            List<Repositorie> repositories = repositorieDao.getAllRepositorie(start_row , batch_size);
            repositories.forEach((x) -> {
                repositorieESDao.save(x);
            });
            record = repositories.size();
            start_row += record;
            return;
        }while (record == batch_size);
    }
}
