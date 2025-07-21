package com.gj.miclrn.identifies;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.gj.miclrn.entities.BaseEntity;

public class IdeGenerator implements IdentifierGenerator {
    private static final long serialVersionUID = 1L;

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        if (object == null)
            throw new HibernateException(new NullPointerException());

        if ((((BaseEntity) object).getId()) == null) {
            return sequenceGenerator.getInstance().nextId();
        } else {
            return ((BaseEntity) object).getId();

        }
    }

}