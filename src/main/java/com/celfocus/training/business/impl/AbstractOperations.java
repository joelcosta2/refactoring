package com.celfocus.training.business.impl;

import com.celfocus.training.business.IOperations;
import com.celfocus.training.business.exception.DeleteException;
import com.celfocus.training.business.exception.SaveException;

import java.util.ArrayList;
import java.util.List;


import static com.celfocus.training.util.constant.ConstantNumbers.*;

abstract class AbstractOperations<T> implements IOperations<T> {

    private List<T> mapModel;

    public AbstractOperations() {
        this.mapModel = new ArrayList<>();
    }

    @Override
    public List<T> getAll() {
        return this.mapModel;
    }

    @Override
    public void save(T model) throws SaveException {
        this.mapModel.add(model);
    }

    @Override
    public void update(int indexInList, T model) {
        this.mapModel.set(indexInList, model);
    }

    @Override
    public void delete(int index) throws DeleteException {
        this.mapModel.remove(index);
    }

    public int getNextIndex() {
        return mapModel.isEmpty() ? START_ID : mapModel.size() + 1;
    }

    public int getIndex(T model) {
        return this.mapModel.indexOf(model);
    }

}
