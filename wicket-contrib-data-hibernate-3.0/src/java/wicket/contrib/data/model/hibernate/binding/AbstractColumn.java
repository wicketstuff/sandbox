package wicket.contrib.data.model.hibernate.binding;

public abstract class AbstractColumn implements IColumn {
	private String displayName;
	private String modelPath;
	private String orderByPath;
	private boolean allowOrderBy = true;
	
	public boolean allowOrderBy() {
		return allowOrderBy;
	}
	
	public IColumn setAllowOrderBy(boolean allowOrderBy) {
		this.allowOrderBy = allowOrderBy;
		return this;
	}
	
	public IColumn setOrderByPath(String orderByPath) {
		this.orderByPath = orderByPath;
		return this;
	}
	
	public AbstractColumn(String displayName, String modelPath) {
		this.displayName = displayName;
		this.modelPath = modelPath;
		this.orderByPath = modelPath;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getModelPath() {
		return modelPath;
	}
	
	public String getOrderByPath() {
		return orderByPath;
	}
}
