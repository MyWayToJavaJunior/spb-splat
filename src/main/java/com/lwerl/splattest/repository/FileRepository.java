package com.lwerl.splattest.repository;

import com.lwerl.splattest.model.File;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FileRepository {

    @Transactional(readOnly = true)
    File get(Long id);

    Long create(File file);

    void update(File file);

    void delete(File file);
}
