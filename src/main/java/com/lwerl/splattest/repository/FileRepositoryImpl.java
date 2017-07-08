package com.lwerl.splattest.repository;

import com.lwerl.splattest.model.File;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class FileRepositoryImpl implements FileRepository {

    @Resource
    private
    SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public File get(Long id) {
        Session session = getSession();
        return session.get(File.class, id);
    }

    @Override
    public Long create(File file) {
        Session session = getSession();
        return (Long) session.save(file);

    }

    @Override
    public void update(File file) {
        Session session = getSession();
        session.update(file);
    }

    @Override
    public void delete(File file) {
        Session session = getSession();
        session.delete(file);
    }
}
