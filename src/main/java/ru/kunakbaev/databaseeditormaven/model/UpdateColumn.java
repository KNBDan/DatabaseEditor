package ru.kunakbaev.databaseeditormaven.model;

public class UpdateColumn {
    private Column firstCondition;
    private Column updatedCondition;

    public UpdateColumn(Column firstCondition, Column updatedCondition){
        this.firstCondition = firstCondition;
        this.updatedCondition = updatedCondition;
    }

    public Column getFirstCondition() {
        return firstCondition;
    }

    public void setFirstCondition(Column firstCondition) {
        this.firstCondition = firstCondition;
    }

    public Column getUpdatedCondition() {
        return updatedCondition;
    }

    public void setUpdatedCondition(Column updatedCondition) {
        this.updatedCondition = updatedCondition;
    }
}
