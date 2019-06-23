package net.mattheard.alphabetafilter;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.data.Set;

import java.util.List;

class ChartDataSetUpdater implements Runnable {
    private final Set set;
    private final List<DataEntry> seriesData;

    ChartDataSetUpdater(Set set, List<DataEntry> seriesData) {
        this.set = set;
        this.seriesData = seriesData;
    }

    @Override
    public void run() {
        set.data(seriesData);
    }
}
