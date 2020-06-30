package com.marvel.services;

import com.marvel.api.v1.model.QueryCharacterModel;
import com.marvel.exceptions.BadParametersException;
import org.springframework.stereotype.Service;

@Service
public class ModelService {

    private final String DEFAULT_ORDER_BY = "name";
    private final String START_TIME = "1900-01-01 00:00:00";
    private final String END_TIME = "2900-01-01 00:00:00";
    private final Integer DEFAULT_PAGE_SIZE = 15;
    private final Integer DEFAULT_NUMBER_PAGE = 0;

    public QueryCharacterModel setParametersIntoModel(String numberPage,
                                                      String pageSize,
                                                      String orderBy,
                                                      String modifiedFrom,
                                                      String modifiedTo) throws BadParametersException {

        QueryCharacterModel model = new QueryCharacterModel();

        try {
            if (numberPage != null)
                model.setNumberPage(Integer.valueOf(numberPage));
            else
                model.setNumberPage(DEFAULT_NUMBER_PAGE);

            if (pageSize != null)
                model.setPageSize(Integer.valueOf(pageSize));
            else
                model.setPageSize(DEFAULT_PAGE_SIZE);

            if (orderBy != null)
                model.setOrderBy(orderBy);
            else
                model.setOrderBy(DEFAULT_ORDER_BY);

            if (modifiedFrom != null)
                model.setModifiedFrom(modifiedFrom);
            else
                model.setModifiedFrom(START_TIME);

            if (modifiedTo != null)
                model.setModifiedTo(modifiedTo);
            else
                model.setModifiedTo(END_TIME);
        } catch (Exception e) {
            throw new BadParametersException("invalid url parameters");
        }

        return model;
    }
}
