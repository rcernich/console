/* 
 * JBoss, Home of Professional Open Source 
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved. 
 * See the copyright.txt in the distribution for a 
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use, 
 * modify, copy, or redistribute it subject to the terms and conditions 
 * of the GNU Lesser General Public License, v. 2.1. 
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details. 
 * You should have received a copy of the GNU Lesser General Public License, 
 * v.2.1 along with this distribution; if not, write to the Free Software 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 */
package org.switchyard.console.client.ui.metrics;

import java.util.List;

import org.jboss.as.console.client.core.DisposableViewImpl;
import org.jboss.as.console.client.shared.viewframework.builder.SimpleLayout;
import org.jboss.ballroom.client.widgets.window.DefaultWindow;
import org.switchyard.console.client.model.MessageMetrics;
import org.switchyard.console.client.model.ServiceMetrics;
import org.switchyard.console.client.ui.metrics.MetricsPresenter.MyView;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

/**
 * RuntimeView
 * 
 * <p/>
 * View implementation for SwitchYard runtime metrics.
 * 
 * @author Rob Cernich
 */
public class MetricsView extends DisposableViewImpl implements MyView {

    private MessageMetricsViewer _systemMetricsViewer;
    private ServiceMetricsList _servicesList;
    private ServiceMetricsList _referencesList;
    private MessageMetricsViewer _referenceMetricsViewer;
    private ServiceOperationMetricsList _referenceOperationMetricsList;
    private MessageMetrics _systemMetrics;
    private DefaultWindow _serviceDetailsWindow;
    private ServiceDetailsWidget _serviceDetailsWidget;
    private DefaultWindow _referenceDetailsWindow;
    private ReferenceDetailsWidget _referenceDetailsWidget;

    @Override
    public Widget createWidget() {
        createServiceDetailsWindow();
        createReferenceDetailsWindow();

        _systemMetricsViewer = new MessageMetricsViewer(false);
        _servicesList = new ServiceMetricsList("Service Metrics");
        _referencesList = new ServiceMetricsList("Reference Metrics");
        _referenceMetricsViewer = new MessageMetricsViewer(true);
        _referenceOperationMetricsList = new ServiceOperationMetricsList();

        _servicesList.addSelectionChangeHandler(new Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                showServiceDetails(_servicesList.getSelection());
            }
        });

        _referencesList.addSelectionChangeHandler(new Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                showReferenceDetails(_referencesList.getSelection());
            }
        });

        SimpleLayout layout = new SimpleLayout().setTitle("SwitchYard Message Metrics").setHeadline("System")
                .setDescription("Displays message metrics for the SwitchYard subsystem.")
                .addContent("System Message Metrics", _systemMetricsViewer.asWidget())
                .addContent("spacer", new HTMLPanel("&nbsp;"))
                .addContent("Service Message Metrics", _servicesList.asWidget())
                .addContent("Reference Message Metrics", _referencesList.asWidget());

        return layout.build();
    }

    @Override
    public void setPresenter(MetricsPresenter presenter) {
    }

    @Override
    public void setServices(List<ServiceMetrics> serviceMetrics) {
        _servicesList.setData(serviceMetrics);
    }

    @Override
    public void setReferences(List<ServiceMetrics> referenceMetrics) {
        _referencesList.setData(referenceMetrics);
    }

    @Override
    public void setReferenceMetrics(ServiceMetrics serviceMetrics) {
        if (serviceMetrics == null) {
            _referenceMetricsViewer.clear();
            _referenceOperationMetricsList.setServiceMetrics(null);
            return;
        }
        if (_systemMetrics == null) {
            _referenceMetricsViewer.setMessageMetrics(serviceMetrics);
        } else {
            _referenceMetricsViewer.setMessageMetrics(serviceMetrics, _systemMetrics.getTotalCount(),
                    _systemMetrics.getTotalProcessingTime());
        }
        _referenceOperationMetricsList.setServiceMetrics(serviceMetrics);
    }

    @Override
    public void setSystemMetrics(MessageMetrics systemMetrics) {
        _systemMetrics = systemMetrics;
        if (systemMetrics == null) {
            _systemMetricsViewer.clear();
            return;
        }
        _systemMetricsViewer.setMessageMetrics(systemMetrics);
        _servicesList.setSystemMetrics(systemMetrics);
        _referencesList.setSystemMetrics(systemMetrics);
    }

    @Override
    public void clearMetrics() {
        _systemMetricsViewer.clear();
    }

    private void showServiceDetails(ServiceMetrics metrics) {
        _serviceDetailsWidget.setMetrics(metrics, _systemMetrics);
        _serviceDetailsWindow.center();
    }

    private void createServiceDetailsWindow() {
        _serviceDetailsWindow = new DefaultWindow("Service Metrics");
        _serviceDetailsWindow.setGlassEnabled(true);
        _serviceDetailsWindow.setAutoHideEnabled(true);
        _serviceDetailsWindow.setAutoHideOnHistoryEventsEnabled(true);
        _serviceDetailsWindow.setWidth(600);
        _serviceDetailsWindow.setHeight(360);

        _serviceDetailsWidget = new ServiceDetailsWidget();
        _serviceDetailsWindow.setWidget(_serviceDetailsWidget.asWidget());
    }

    private void showReferenceDetails(ServiceMetrics metrics) {
        _referenceDetailsWidget.setMetrics(metrics, _systemMetrics);
        _referenceDetailsWindow.center();
    }

    private void createReferenceDetailsWindow() {
        _referenceDetailsWindow = new DefaultWindow("Reference Metrics");
        _referenceDetailsWindow.setGlassEnabled(true);
        _referenceDetailsWindow.setAutoHideEnabled(true);
        _referenceDetailsWindow.setAutoHideOnHistoryEventsEnabled(true);
        _referenceDetailsWindow.setWidth(600);
        _referenceDetailsWindow.setHeight(360);

        _referenceDetailsWidget = new ReferenceDetailsWidget();
        _referenceDetailsWindow.setWidget(_referenceDetailsWidget.asWidget());
    }

}
