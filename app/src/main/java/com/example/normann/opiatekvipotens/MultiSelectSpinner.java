package com.example.normann.opiatekvipotens;

/**
 * Created by Morten on 22.12.2014.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

/**
 * A Spinner view that does not dismiss the dialog displayed when the control is "dropped down"
 * and the user presses it. This allows for the selection of more than one option.
 */
public class MultiSelectSpinner extends Spinner implements OnMultiChoiceClickListener {
    String[] _items = null;
    boolean[] _selection = null;

    ArrayAdapter<String> _proxyAdapter;

    /**
     * Constructor for use when instantiating directly.
     * @param context
     */
    public MultiSelectSpinner(Context context) {
        super(context);

        _proxyAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item);
        super.setAdapter(_proxyAdapter);
    }

    /**
     * Constructor used by the layout inflater.
     * @param context
     * @param attrs
     */
    public MultiSelectSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        _proxyAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item);
        super.setAdapter(_proxyAdapter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (_selection != null && which < _selection.length) {
            _selection[which] = isChecked;

            _proxyAdapter.clear();
            _proxyAdapter.add(buildSelectedItemString());
            setSelection(0);
        }
        else {
            throw new IllegalArgumentException("Argument 'which' is out of bounds.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(_items, _selection, this);
        builder.setTitle("Opioider");
        builder.setNeutralButton("Fjern alle",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
        builder.setNegativeButton("Markér alle",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean wantToCloseDialog = false;
                        selectAll(false, dialog);

                        if (wantToCloseDialog)
                            dialog.dismiss();
                    }
                });
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean wantToCloseDialog = false;
                        selectAll(true, dialog);

                        if (wantToCloseDialog)
                            dialog.dismiss();
                    }
                });
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean wantToCloseDialog = true;
                        if (wantToCloseDialog)
                            dialog.dismiss();
                    }
                });
        return true;
    }

    protected void selectAll(boolean isSelectAll, AlertDialog dialog) {
        if (_selection != null) {
            for (int i = 0; i < _items.length; i++) {
                _selection[i] = isSelectAll;
                ((AlertDialog) dialog).getListView().setItemChecked(i, isSelectAll);

            }
            _proxyAdapter.clear();
            _proxyAdapter.add(buildSelectedItemString());
        } else {
            throw new IllegalArgumentException("mSelection is null");
        }
    }

    /**
     * MultiSelectSpinner does not support setting an adapter. This will throw an exception.
     * @param adapter
     */
    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException("setAdapter is not supported by MultiSelectSpinner.");
    }

    /**
     * Sets the options for this spinner.
     * @param items
     */
    public void setItems(String[] items) {
        _items = items;
        _selection = new boolean[_items.length];

        Arrays.fill(_selection, false);
    }

    /**
     * Sets the options for this spinner.
     * @param items
     */
    public void setItems(List<String> items) {
        _items = items.toArray(new String[items.size()]);
        _selection = new boolean[_items.length];

        Arrays.fill(_selection, false);
    }

    /**
     * Sets the selected options based on an array of string.
     * @param selection
     */
    public void setSelection(String[] selection) {
        for (String sel : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(sel)) {
                    _selection[j] = true;
                }
            }
        }
    }

    /**
     * Sets the selected options based on a list of string.
     * @param selection
     */
    public void setSelection(List<String> selection) {
        for (String sel : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(sel)) {
                    _selection[j] = true;
                }
            }
        }
    }

    /**
     * Sets the selected options based on an array of positions.
     * @param selectedIndicies
     */
    public void setSelection(int[] selectedIndicies) {
        for (int index : selectedIndicies) {
            if (index >= 0 && index < _selection.length) {
                _selection[index] = true;
            }
            else {
                throw new IllegalArgumentException("Index " + index + " is out of bounds.");
            }
        }
    }

    /**
     * Returns a list of strings, one for each selected item.
     * @return
     */
    public ArrayList<String> getSelectedStrings() {
        ArrayList<String> selection = new ArrayList<String>();
        for (int i = 0; i < _items.length; ++i) {
            if (_selection[i]) {
                selection.add(_items[i]);
            }
        }
        return selection;
    }

    /**
     * Returns a list of positions, one for each selected item.
     * @return
     */
    public List<Integer> getSelectedIndicies() {
        List<Integer> selection = new LinkedList<Integer>();
        for (int i = 0; i < _items.length; ++i) {
            if (_selection[i]) {
                selection.add(i);
            }
        }
        return selection;
    }

    /**
     * Builds the string for display in the spinner.
     * @return comma-separated list of selected items
     */
    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < _items.length; ++i) {
            if (_selection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;

                sb.append(_items[i]);
            }
        }

        return sb.toString();
    }
}
