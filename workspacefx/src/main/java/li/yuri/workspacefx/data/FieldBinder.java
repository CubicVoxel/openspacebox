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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Binds {@link li.yuri.workspacefx.data.IsField}s to objects, resulting in a more convenient and less
 * boilerplate-intensive translation from bean to field and back.
 * <p>
 * Use {@link #bind(li.yuri.workspacefx.data.IsField, li.yuri.workspacefx.data.Binding.Setter,
 * li.yuri.workspacefx.data.Binding.Getter)} to bind any {@link li.yuri.workspacefx.data.IsField} to a setter and getter
 * of the BEAN.
 * <p>
 * {@link #writeToBean(Object)} writes the fields' values to the bound properties of the given BEAN.
 * <p>
 * {@link #readFromBean(Object)} sets the fields' values to the bound properties of the given BEAN.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 *
 * private void createFields() {
 *      binder = new FieldBinder<>();
 *
 *      idField = new TextField();
 *      binder.bind(idField, StationType::setId, StationType::getId);
 *
 *      displayNameField = new TextField();
 *      binder.bind(displayNameField, StationType::setDisplayName, StationType::getDisplayName);
 *
 *      cargoSpaceField = new IntegerField();
 *      binder.bind(cargoSpaceField, StationType::setCargoSpace, StationType::getCargoSpace);
 * }
 *
 * public void setValue(StationType value) {
 *      binder.readFromBean(value);
 * }
 *
 * public StationType getValue() {
 *      StationType stationType = new StationType();
 *      binder.writeToBean(stationType);
 *      return stationType;
 * }
 *
 * }
 * </pre>
 */
public class FieldBinder<BEAN> {

    private HashMap<li.yuri.workspacefx.data.IsField<?>, li.yuri.workspacefx.data.Binding<BEAN, ?>> bindings = new HashMap<>();

    /**
     * Binds any {@link li.yuri.workspacefx.data.IsField} to a given {@link li.yuri.workspacefx.data.Binding.Setter} and
     * {@link li.yuri.workspacefx.data.Binding.Getter} of a bean.
     *
     * @param <VALUETYPE> the type of the property. You can most likely infer the type parameter.
     * @param field       the field to bind
     * @param setter      applies the field's values to the bean
     * @param getter      returns the bean's value
     */
    public <VALUETYPE> li.yuri.workspacefx.data.Binding<BEAN, VALUETYPE> bind(
            li.yuri.workspacefx.data.IsField<VALUETYPE> field,
            li.yuri.workspacefx.data.Binding.Setter<BEAN, VALUETYPE> setter,
            li.yuri.workspacefx.data.Binding.Getter<BEAN, VALUETYPE> getter
    ) {
        li.yuri.workspacefx.data.Binding<BEAN, VALUETYPE> binding = new li.yuri.workspacefx.data.Binding<BEAN, VALUETYPE>() {
            private List<Validation<BEAN>> validations = new ArrayList<>();

            @Override
            public li.yuri.workspacefx.data.IsField<VALUETYPE> getField() {
                return field;
            }

            @Override
            public Setter<BEAN, VALUETYPE> getSetter() {
                return setter;
            }

            @Override
            public Getter<BEAN, VALUETYPE> getGetter() {
                return getter;
            }

            @Override
            public List<Validation<BEAN>> getValidations() {
                return validations;
            }

        };

        bindings.put(field, binding);

        return binding;
    }

    /**
     * Only checks all validators without giving the user feedback.
     *
     * @param bean
     * @return
     */
    public boolean isValid(BEAN bean) {
        return bindings.values().stream().allMatch(binding -> binding.isValid(bean));
    }

    /**
     * Checks all validators and marks the respective fields as invalid.
     *
     * @param bean
     * @return
     */
    public boolean validate(BEAN bean) {
        boolean allValid = true;
        for (Map.Entry<IsField<?>, li.yuri.workspacefx.data.Binding<BEAN, ?>> entry : bindings.entrySet()) {
            entry.getKey().resetInvalid();
            final List<String> failedValidations = entry.getValue()
                    .getValidations()
                    .stream()
                    .filter(validation -> !validation.getValidator().isValid(bean))
                    .map(Binding.Validation::getMessage)
                    .collect(Collectors.toList());
            if (!failedValidations.isEmpty()) {
                allValid = false;
                entry.getKey().showInvalid(failedValidations);
            }
        }
        return allValid;
    }

    /**
     * Writes the fields' values to the bound properties of the given bean.
     *
     * @param bean the bean to store values in
     */
    public void writeToBean(BEAN bean) {
        bindings.values().forEach(beanBinding -> beanBinding.applyToBean(bean));
    }

    /**
     * Sets the bound fields' values to the respective properties of the given bean.
     *
     * @param bean the bean to get values from
     */
    public void readFromBean(BEAN bean) {
        bindings.values().forEach(beanBinding -> beanBinding.applyToField(bean));
    }


}
