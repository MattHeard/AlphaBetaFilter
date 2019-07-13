package net.mattheard.alphabetafilter;

import android.text.Editable;
import android.text.TextWatcher;

class InitialModelValueFieldListener implements TextWatcher {
    private final Model model;

    InitialModelValueFieldListener(Model model) {
        this.model = model;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            model.value = Float.parseFloat(s.toString());
        } catch (NumberFormatException e) {
            // Leave model value unchanged
        }
    }
}
