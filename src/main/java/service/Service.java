package service;

import entity.BaseEntity;

public interface Service {

    void saveOrUpdate(BaseEntity baseEntity);
    void delete(BaseEntity baseEntity);
}
