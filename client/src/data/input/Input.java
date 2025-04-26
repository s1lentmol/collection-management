package data.input;

import exceptions.*;
/*
Абстрактный класс формы для ввода пользовательских данных.
@param <T> создаваемый объект
 */
public abstract class Input<T> {
    public abstract T build() throws IncorrectInputInScriptException, InvalidFormException;
}
