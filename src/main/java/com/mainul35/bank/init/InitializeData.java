package com.mainul35.bank.init;

public interface InitializeData {

    void initialize();

    default void doCleanUp () {

    }
}
