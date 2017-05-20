# ExRecyclerView
--------

#### CommonRecyclerHolder
通用的ViewHolder

#### HolderAdapter  BaseRecyclerAdapter<T>
集成通用ViewHolder的Adapter      
集成List<T>和布局的Adapter

#### HeaderAndFooterRecyclerViewAdapter
可以设置HeadView和FooterView的Adapter


#### RecyclerClickSupport 设置监听
```
RecyclerClickSupport.addTo(mRecyclerView)
        .setOnItemClickListener(new RecyclerClickSupport.OnItemClickListener());
```

#### EndlessRecyclerOnScrollListener
滚动到页面末尾的监听

