package net.datafans.android.common.widget.table;

import java.util.HashMap;
import java.util.Map;

import net.datafans.android.common.widget.table.refresh.ListViewAdapter;
import net.datafans.android.common.widget.table.refresh.ListViewListener;
import net.datafans.android.common.widget.table.refresh.RefreshControlType;
import net.datafans.android.common.widget.table.refresh.adapter.BGAListViewAdapter;
import net.datafans.android.common.widget.table.refresh.adapter.BGAListViewAdapter.RefreshType;
import net.datafans.android.common.widget.table.refresh.adapter.DropDownListViewAdapter;
import net.datafans.android.common.widget.table.refresh.adapter.PullDownListViewAdapter;
import net.datafans.android.common.widget.table.refresh.adapter.PullToRefreshListViewAdapter;
import net.datafans.android.common.widget.table.refresh.adapter.SwipeRefreshListViewAdapter;
import net.datafans.android.common.widget.table.refresh.adapter.UltraPullToRefreshListViewAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TableView<T> implements ListViewListener {

	private Map<RefreshControlType, ListViewAdapter> adapterMap = new HashMap<RefreshControlType, ListViewAdapter>();

	private RefreshControlType refreshType;

	private TableViewAdapter tableViewAdapter;
	private TableViewDataSource<T> dataSource;
	private TableViewDelegate delegate;

	private Context context;

	public TableView(Context context, RefreshControlType type) {
		this.context = context;
		refreshType = type;
		init();
	}

	public TableView(Context context) {
		this.context = context;
		this.refreshType = RefreshControlType.PullToRefresh;
		init();
	}

	private void init() {
		tableViewAdapter = new TableViewAdapter();
		initView();
	}

	private ListViewAdapter getAdapter(RefreshControlType type) {
		ListViewAdapter adapter = adapterMap.get(type);
		if (adapter == null) {
			switch (type) {
			case PullDown:
				adapter = new PullDownListViewAdapter(context, tableViewAdapter);
				break;
			case PullToRefresh:
				adapter = new PullToRefreshListViewAdapter(context,
						tableViewAdapter);
				break;
			case SwipeRefresh:
				adapter = new SwipeRefreshListViewAdapter(context,
						tableViewAdapter);
				break;
			case UltraPullToRefresh:
				adapter = new UltraPullToRefreshListViewAdapter(context,
						tableViewAdapter);
				break;
			case DropDown:
				adapter = new DropDownListViewAdapter(context, tableViewAdapter);
				break;
			case BGANormal:
				adapter = new BGAListViewAdapter(context, tableViewAdapter,
						RefreshType.Normal);
				break;
			case BGAMooc:
				adapter = new BGAListViewAdapter(context, tableViewAdapter,
						RefreshType.MoocStyle);
				break;
			case BGAStickiness:
				adapter = new BGAListViewAdapter(context, tableViewAdapter,
						RefreshType.Stickiness);
				break;
			default:
				break;
			}

			adapter.enableRefresh(true);
			adapter.enableLoadMore(true);
			adapter.enableAutoLoadMore(false);

			adapterMap.put(type, adapter);
		}
		return adapter;
	}

	private ListViewAdapter getAdapter() {
		return getAdapter(refreshType);
	}

	public View getView() {
		return getAdapter().getListView();
	}

	public BaseAdapter getTableViewAdapter() {
		return tableViewAdapter;
	}

	public void reloadData() {
		tableViewAdapter.notifyDataSetChanged();
	}

	private void initView() {

		ListViewAdapter adapter = getAdapter();
		adapter.setListener(this);
	}

	private class TableViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (dataSource == null) {
				return 0;
			}
			return dataSource.getRows();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("unchecked")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view = convertView;
			TableViewCell<T> cell = null;
			if (view == null) {
				cell = dataSource.getTableViewCell(position);
				view = ((TableViewCell<T>) cell).getView();
				view.setTag(cell);
			} else {
				cell = (TableViewCell<T>) view.getTag();
			}

			cell.refresh((T) dataSource.getEntity(position));

			return view;
		}
	}

	public TableViewDataSource<T> getDataSource() {
		return dataSource;
	}

	public void setDataSource(TableViewDataSource<T> dataSource) {
		this.dataSource = dataSource;
	}

	public TableViewDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(TableViewDelegate delegate) {
		this.delegate = delegate;
	}

	@Override
	public void onRefresh() {

		if (delegate == null) {
			return;
		}

		delegate.onRefresh();
	}

	@Override
	public void onLoadMore() {
		if (delegate == null) {
			return;
		}

		delegate.onLoadMore();
	}

	public void endRefresh() {
		getAdapter().endRefresh();
	}

	public void endLoadMore() {
		getAdapter().endLoadMore();
	}

	public void loadOver(boolean over) {
		getAdapter().loadOver(over);
	}

}
