# NodeSeekBar
节点托动条

这是一个有节点的SeekBar，其节点平均分布在SeekBar上。
其实现原理是自定义View，然后复写onDraw在合适地方画圆。
现在可以设置NodeSeekBar的方向，圆的大小、颜色，线段颜色等，当然也能设置节点的数量。

使用方法：

1、在布局文件中添加NodeSeekBar

<com.mouse.cookie.nodeseekbar.NodeSeekBar
        android:id="@+id/nsb_mainactivity"
        android:layout_width="wrap_content"
        android:layout_height="400dp"
        app:nodeNumber="6"
        app:progress="2"
        app:cycleRadius="20"
        app:orientation="vertical"
        app:cycleBackgroundColorBefore="#bbbbbb"
        app:cycleBackgroundColorAfter="#00ffff"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"/>
设置节点数nodeNumber=int，当前进度progress=int，节点圆半径cycleRadius=int，节点圆两种颜色cycleBackgroundColorBefore=color、cycleBackgroundColorAfter=color，方向orientation=(vertical、horizontal)。

2、在java代码中设置进度变化事件监听器

mNodeSeekBar.setOnProgressChangedListener(new NodeSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int i) {
                Log.i("Tag_MainActivity", "进度为：" + i);
            }
        });

3、获取当前进度

mNodeSeekBar.getProgress();

实例：

