[![Donation](https://img.shields.io/badge/donate-please-brightgreen.svg)](https://www.paypal.me/janrabe) [![About Jan Rabe](https://img.shields.io/badge/about-me-green.svg)](https://about.me/janrabe) 
# RecyclerView Presenter [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RecyclerViewPresenter-green.svg?style=true)](https://android-arsenal.com/details/1/3593) [![appetize.io](https://img.shields.io/badge/appetize.io-Live%20Demo-blue.svg)](https://appetize.io/app/twkuv0xydcy5h8whmkcmx81kur) [![](https://jitpack.io/v/kibotu/RecyclerViewPresenter.svg)](https://jitpack.io/#kibotu/RecyclerViewPresenter)  [![Javadoc](https://img.shields.io/badge/javadoc-SNAPSHOT-green.svg)](https://jitpack.io/com/github/kibotu/RecyclerViewPresenter/master-SNAPSHOT/javadoc/index.html) [![Build Status](https://travis-ci.org/kibotu/RecyclerViewPresenter.svg)](https://travis-ci.org/kibotu/RecyclerViewPresenter)  [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15) [![Gradle Version](https://img.shields.io/badge/gradle-5.3-green.svg)](https://docs.gradle.org/current/release-notes)  [![Kotlin](https://img.shields.io/badge/kotlin-1.3.21-green.svg)](https://kotlinlang.org/) [![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/kibotu/RecyclerViewPresenter/master/LICENSE) [![androidx](https://img.shields.io/badge/androidx-brightgreen.svg)](https://developer.android.com/topic/libraries/support-library/refactor)

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
        
        // optional
        compile 'jp.wasabeef:recyclerview-animators:2.2.3'
    }
    
### How to use

1. [Add the PresenterAdapter to your RecyclerView](https://github.com/kibotu/RecyclerViewPresenter/blob/master/app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/MainActivity.java#L36-L38)

        PresenterAdapter<String> adapter = new PresenterAdapter<>();
        list.setAdapter(adapter);
        
2. [Add a model with a Presenter as representation] (https://github.com/kibotu/RecyclerViewPresenter/blob/master/app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/MainActivity.java#L42-L47) to the adapter, e.g.:

        adapter.add(myModelObject, PhotoPresenter.class);
        adapter.add(myModelObject, LabelPresenter.class);
        
3. Create a presenter, e.g. [PhotoPresenter](https://github.com/kibotu/RecyclerViewPresenter/blob/master/app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/PhotoPresenter.java) or [LabelPresenter](https://github.com/kibotu/RecyclerViewPresenter/blob/master/app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/LabelPresenter.java)

        public class MyModelPresenter extends Presenter<MyModel, PhotoPresenter.ViewHolder> 
        
4. Add click listener [to adapter](https://github.com/kibotu/RecyclerViewPresenter/blob/master/app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/MainActivity.java#L40) and [to presenter](https://github.com/kibotu/RecyclerViewPresenter/blob/master/app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/PhotoPresenter.java#L54-L59)

        // adapter
        adapter.setOnItemClickListener((item, rowView, position) -> toast(format("{0}. {1}", position, item)));
        
        // in bind method of presenter
        if (presenterAdapter.getOnItemClickListener() != null)
                    presenterAdapter.getOnItemClickListener().onItemClick(item, v, position);


### [Updating item](https://github.com/kibotu/RecyclerViewPresenter/blob/master/app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/MainActivity.java#L52)

    adapter.update(0, myModelObject);

### [Sorting](https://github.com/kibotu/RecyclerViewPresenter/blob/master/app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/MainActivity.java#L47-L50)     
   
    PresenterAdapter.sort(adapter);
       
Sort if model doesn't implement Comparable
   
    adapter.sortBy((o1, o2) -> o1.compareTo(o2));
       
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