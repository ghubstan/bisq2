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

package bisq.desktop.primary.main.content.dashboard;

import bisq.application.DefaultApplicationService;
import bisq.desktop.common.view.Controller;
import bisq.desktop.common.view.Navigation;
import bisq.desktop.common.view.NavigationTarget;
import bisq.desktop.primary.main.content.newProfilePopup.NewProfilePopup;
import lombok.Getter;

public class DashboardController implements Controller {
    private final DashboardModel model;
    @Getter
    private final DashboardView view;

    private final NewProfilePopup newProfilePopup;

    public DashboardController(DefaultApplicationService applicationService) {
        model = new DashboardModel();
        view = new DashboardView(model, this);

        newProfilePopup = new NewProfilePopup(applicationService);
    }

    @Override
    public void onActivate() {

    }

    @Override
    public void onDeactivate() {

    }

    public void showNewProfilePopup() {
        newProfilePopup.show();
    }

    public void openTradeOverview() {
        Navigation.navigateTo(NavigationTarget.TRADE_OVERVIEW);
    }

    public void openBisqEasy() {
        Navigation.navigateTo(NavigationTarget.BISQ_EASY);
    }
   
}
