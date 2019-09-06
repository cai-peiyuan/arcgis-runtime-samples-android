package com.bohaigaoke.forestry.model;

public class MapLayer {

	public final static int MAP_TILED_2_0 = 1;
	public final static int MAP_TILED_3_0 = 2;
	public final static int MAP_VM = 3;

	private String name;
	private String displayName; // 显示名称
	private int mapType = 0;// 1-2.0瓦片地图(10切图)：EzMapSimpleTiledLayerGeog2010；2-3.0瓦片地图：EzMapTiledLayerGeog2010;3-（3.0）矢量地图:EzMapVMLayer
	private String mapPath; // 离线地图文件路径
	private String dbName; // 2.0接口的数据才需要指定文件名；3.0接口指定数据库路径即可。
	private String styName; // 矢量地图渲染样式文件
	private String url; // 在线地图url
	private String iconName;
	private boolean visible = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getMapType() {
		return mapType;
	}

	public void setMapType(int mapType) {
		this.mapType = mapType;
	}

	public String getMapPath() {
		return mapPath;
	}

	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getStyName() {
		return styName;
	}

	public void setStyName(String styName) {
		this.styName = styName;
	}

}
