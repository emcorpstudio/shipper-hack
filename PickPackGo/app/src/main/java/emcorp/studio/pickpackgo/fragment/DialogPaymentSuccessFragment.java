package emcorp.studio.pickpackgo.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import emcorp.studio.pickpackgo.R;
import emcorp.studio.pickpackgo.utils.Constant;
import emcorp.studio.pickpackgo.utils.SharedPrefManager;

public class DialogPaymentSuccessFragment extends DialogFragment {

    private View root_view;
    private TextView tvCompleteTime, tvDurationTime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.dialog_payment_success, container, false);

        ((FloatingActionButton) root_view.findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvCompleteTime = root_view.findViewById(R.id.tvCompleteTime);
        tvDurationTime = root_view.findViewById(R.id.tvDurationTime);
        tvCompleteTime.setText(SharedPrefManager.getInstance(getContext()).getReferences(Constant.PREFERENCES_COMPLETETIME));
        tvDurationTime.setText(SharedPrefManager.getInstance(getContext()).getReferences(Constant.PREFERENCES_DURATION));

        return root_view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}