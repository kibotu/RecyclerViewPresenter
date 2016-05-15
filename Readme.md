# RecyclerView Presenter [![Build Status](https://travis-ci.org/kibotu/RecyclerViewPresenter.svg?branch=master)](https://travis-ci.org/kibotu/RecyclerViewPresenter) [![](https://jitpack.io/v/kibotu/RecyclerViewPresenter.svg)](https://jitpack.io/#kibotu/RecyclerViewPresenter)  [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15) [![Gradle Version](https://img.shields.io/badge/gradle-2.13-green.svg)](https://docs.gradle.org/current/release-notes) [![Licence](https://img.shields.io/badge/licence-Apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
  
Convenience library to handle different view types with different presenters in a single RecyclerView. 

[![Screenshot](https://raw.githubusercontent.com/kibotu/RecyclerViewPresenter/master/screenshot.png)](https://raw.githubusercontent.com/kibotu/RecyclerViewPresenter/master/screenshot.png)
  
### How to install
	
	repositories {
	    maven {
	        url "https://jitpack.io"
	    }
	}
		
	dependencies {
        compile 'com.github.kibotu:RecyclerViewPresenter:-SNAPSHOT'
    }
    
### How to use

1. [Add the PresenterAdapter to your RecyclerView](https://github.com/kibotu/RecyclerViewPresenter/blob/master/app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/MainActivity.java#L36-L38)

        PresenterAdapter<String> adapter = new PresenterAdapter<>();
        list.setAdapter(adapter);
        
2. [Add Presenter](https://github.com/kibotu/RecyclerViewPresenter/blob/master/app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/MainActivity.java#L42-L47) to the adapter, e.g.:

        adapter.add(myModelObject, PhotoPresenter.class);
        adapter.add(myModelObject, LabelPresenter.class);
        
3. Create a presenter, e.g. [PhotoPresenter](https://github.com/kibotu/RecyclerViewPresenter/blob/master/app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/PhotoPresenter.java) or [LabelPresenter](https://github.com/kibotu/RecyclerViewPresenter/blob/master/app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/LabelPresenter.java)

        public class MyModelPresenter extends Presenter<MyModel, PhotoPresenter.ViewHolder> 
        
4. Add click listener [to adapter](https://github.com/kibotu/RecyclerViewPresenter/blob/master/app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/MainActivity.java#L40) and [to presenter](https://github.com/kibotu/RecyclerViewPresenter/blob/master/app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/PhotoPresenter.java#L50-L55)

        // adapter
        adapter.setOnItemClickListener((item, rowView, position) -> toast(format("{0}. {1}", position, item)));
        
        // in bind method of presenter
        if (presenterAdapter.getOnItemClickListener() != null)
                    presenterAdapter.getOnItemClickListener().onItemClick(item, v, position);
        
       
###License
<pre>
Copyright 2016 Jan Rabe

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</pre>