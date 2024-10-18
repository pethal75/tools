package com.javaservices.tools.web.beans.primefaces;

import java.io.IOException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public abstract class PrimefacesFormBean<ENTITY extends EditableEntity> extends PrimefacesTabBean {

    protected ENTITY entity, origEntity;

    public void initialize(ENTITY entity) {
        this.entity = (ENTITY) entity.clone();
        this.origEntity = (ENTITY) entity.clone();
    }

    public boolean isChanged() {
        return !entity.equals(origEntity);
    }

    public abstract String getUrl();
    public abstract String getBackUrl();
    public abstract void save() throws Exception;

    // Cancel action, revert changes
    public void cancel() throws IOException {
        this.entity = (ENTITY) this.origEntity.clone();

        this.redirect(getUrl());
    }

    // Back action
    public void back() throws IOException {
        this.redirect(getBackUrl());
    }

}
