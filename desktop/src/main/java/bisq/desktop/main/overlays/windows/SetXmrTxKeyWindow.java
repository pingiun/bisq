/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package bisq.desktop.main.overlays.windows;

import bisq.desktop.components.InputTextField;
import bisq.desktop.main.overlays.Overlay;
import bisq.desktop.util.validation.RegexValidator;

import bisq.core.locale.Res;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import javafx.geometry.HPos;
import javafx.geometry.Insets;

import javax.annotation.Nullable;

import static bisq.common.app.DevEnv.isDevMode;
import static bisq.desktop.util.FormBuilder.addInputTextField;

public class SetXmrTxKeyWindow extends Overlay<SetXmrTxKeyWindow> {

    private InputTextField txHashInputTextField, txKeyInputTextField;

    public SetXmrTxKeyWindow() {
        type = Type.Attention;
    }

    public void show() {
        if (headLine == null)
            headLine = Res.get("setXMRTxKeyWindow.headline");

        width = 868;
        createGridPane();
        addHeadLine();
        addContent();
        addButtons();

        RegexValidator regexValidator = new RegexValidator();
        regexValidator.setPattern("[a-fA-F0-9]{64}");
        regexValidator.setErrorMessage("Input must be a 32 byte hexadeximal number");
        txHashInputTextField.setValidator(regexValidator);
        txKeyInputTextField.setValidator(regexValidator);
        if (isDevMode()) {
            // pre-populate the fields with test data when in dev mode
            txHashInputTextField.setText("e8dcd8160aee016d8a0d9c480355d65773dc577313a0af8237c35f9d997b01c0");
            txKeyInputTextField.setText("300fa18ff99b32ff097d75c64d62732bdb486af8c225f558ee48c5f777f9b509");
        }

        applyStyles();
        display();
    }

    @Override
    protected void createGridPane() {
        gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(64, 64, 64, 64));
        gridPane.setPrefWidth(width);

        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setHalignment(HPos.RIGHT);
        columnConstraints1.setHgrow(Priority.SOMETIMES);
        gridPane.getColumnConstraints().addAll(columnConstraints1);
    }

    @Nullable
    public String getTxHash() {
        return txHashInputTextField != null ? txHashInputTextField.getText() : null;
    }

    @Nullable
    public String getTxKey() {
        return txKeyInputTextField != null ? txKeyInputTextField.getText() : null;
    }

    private void addContent() {
        txHashInputTextField = addInputTextField(gridPane, ++rowIndex, Res.get("setXMRTxKeyWindow.txHash"), 10);
        txKeyInputTextField = addInputTextField(gridPane, ++rowIndex, Res.get("setXMRTxKeyWindow.txKey"));
    }
}
