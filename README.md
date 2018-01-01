TimeTable 
===================
[![](https://jitpack.io/v/EunsilJo/TimeTable.svg)](https://jitpack.io/#EunsilJo/TimeTable) [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)

Android Library that shows simple time table.

## How to import
Add it in your root build.gradle at the end of repositories:
```java
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Add the dependency
```java
dependencies {
	compile 'com.github.EunsilJo:TimeTable:1.0'
}
```

## How to use
### TimeTableView

<img src="https://github.com/EunsilJo/TimeTable/blob/master/screenshots/1.png?raw=true" height="400"/> <img src="https://github.com/EunsilJo/TimeTable/blob/master/screenshots/2.png?raw=true" height="400"/>

```java
timeTable.setStartHour(4);
```
You can change start time. (default 0)
```java
timeTable.setShowHeader(true);
```
You can show/hidden header. (default false)

<img src="https://github.com/EunsilJo/TimeTable/blob/master/screenshots/3.png?raw=true" height="400"/> <img src="https://github.com/EunsilJo/TimeTable/blob/master/screenshots/4.png?raw=true" height="400"/>

```java
timeTable.setTableMode(TimeTableView.TableMode.LONG);
timeTable.setTableMode(TimeTableView.TableMode.SHORT);
```
In short mode, time of table item is hidden. (default LONG)
```java
timeTable.setTimeTable(getMillis("2017-11-10 00:00:00"), mLongSamples);
```
* *long date* : the reference date
* *ArrayList<TimeTableData> times* : items of time table


### TimeData
```java
new TimeData(1, "English", R.color.color_table_2, R.color.white, getMillis("2017-11-10 07:30:00"), getMillis("2017-11-10 08:55:00"));
```
* *T key* : the index of item
* *String title* : the title of item
* *@ColorRes int colorRes* : the background color of item
* *@ColorRes int textColorRes* : the text color of item
* *long startMills* : the start time of item
* *long stopMills* : the end time of item
```java
    .setShowError(true);
```
You can stress this item with error icon.


### TimeTableData
```java
new TimeTableData("Plan", values);
```
* *String header* : the header of item
* *ArrayList<TimeData> values* : items of time


### OnTimeItemClickListener

<img src="https://github.com/EunsilJo/TimeTable/blob/master/screenshots/5.png?raw=true" height="400"/>

```java
timeTable.setOnTimeItemClickListener(new TimeTableItemViewHolder.OnTimeItemClickListener() {
    @Override
    public void onTimeItemClick(View view, int position, TimeGridData item) {
        TimeData time = item.getTime();
        showToast(MainActivity.this,
                time.getTitle() + ", " + new DateTime(time.getStartMills()).toString() +
                " ~ " + new DateTime(time.getStopMills()).toString());
    }
});
```
You can set OnTimeItemClickListener and know clicked item.

### +
Please check the demo app to see examples.