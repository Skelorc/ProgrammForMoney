package service;

import entity.BaseEntity;

public interface Service {

    void saveEntity(BaseEntity baseEntity);
    void updateEntity(BaseEntity baseEntity);
    void delete(BaseEntity baseEntity);
}
