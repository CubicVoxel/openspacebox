/*
 * This file is part of OpenSpaceBox.
 * Copyright (C) 2019 by Yuri Becker <hi@yuri.li>
 *
 * OpenSpaceBox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenSpaceBox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenSpaceBox.  If not, see <http://www.gnu.org/licenses/>.
 */

package li.yuri.workspacefx.data;

import java.util.List;
import java.util.Objects;

public interface Binding<BEAN, VALUETYPE> {
    IsField<VALUETYPE> getField();
    Setter<BEAN, VALUETYPE> getSetter();
    Getter<BEAN, VALUETYPE> getGetter();
    List<Validation<BEAN>> getValidations();

    default void applyToField(BEAN bean) {
        getField().setValue(getGetter().get(bean));
    }

    default void applyToBean(BEAN bean) {
        getSetter().set(bean, getField().getValue());
    }


    default Binding<BEAN, VALUETYPE> withBeanValidator(Validator<BEAN> validator) {
        return withBeanValidator(validator, "");
    }

    default Binding<BEAN, VALUETYPE> withBeanValidator(Validator<BEAN> validator, String message) {
        getValidations().add(new Validation<BEAN>() {
            @Override
            public Validator<BEAN> getValidator() {
                return validator;
            }

            @Override
            public String getMessage() {
                return message;
            }
        });
        return this;
    }

    default Binding<BEAN, VALUETYPE> withValidator(Validator<VALUETYPE> validator, String message) {

        final FieldValidator<BEAN, VALUETYPE>
                fieldValidator = new FieldValidator<BEAN, VALUETYPE>() {
            @Override
            public Validator<VALUETYPE> getValueValidator() {
                return validator;
            }

            @Override
            public Getter<BEAN, VALUETYPE> getGetter() {
                return Binding.this.getGetter();
            }
        };

        final Validation<BEAN> validation = new Validation<BEAN>() {
            @Override
            public Validator<BEAN> getValidator() {
                return fieldValidator;
            }

            @Override
            public String getMessage() {
                return message;
            }
        };
        getValidations().add(validation);

        return this;
    }

    default Binding<BEAN, VALUETYPE> withNotNullValidator(String message) {
        return withValidator(Objects::nonNull, message);
    }

    default boolean isValid(BEAN value) {
        return getValidations().stream().allMatch(validation -> validation.getValidator().isValid(value));
    }

    interface Validation<T> {
        Validator<T> getValidator();

        String getMessage();
    }

    @FunctionalInterface
    interface Validator<T> {
        boolean isValid(T value);

    }

    interface FieldValidator<BEAN, VALUETYPE> extends Validator<BEAN> {
        @Override
        default boolean isValid(BEAN value) {
            return getValueValidator().isValid(getGetter().get(value));
        }

        Validator<VALUETYPE> getValueValidator();

        Getter<BEAN, VALUETYPE> getGetter();
    }

    @FunctionalInterface
    interface Setter<BEAN, VALUETYPE> {
        void set(BEAN bean, VALUETYPE value);
    }

    @FunctionalInterface
    interface Getter<BEAN, VALUETYPE> {
        VALUETYPE get(BEAN bean);
    }
}
