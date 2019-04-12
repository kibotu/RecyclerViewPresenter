[![Donation](https://img.shields.io/badge/buy%20me%20a%20coffee-brightgreen.svg)](https://www.paypal.me/janrabe/5) [![About Jan Rabe](https://img.shields.io/badge/about-me-green.svg)](https://about.me/janrabe)
# RecyclerView Presenter [![](https://jitpack.io/v/kibotu/RecyclerViewPresenter.svg)](https://jitpack.io/#kibotu/RecyclerViewPresenter) [![](https://jitpack.io/v/kibotu/RecyclerViewPresenter/month.svg)](https://jitpack.io/#kibotu/RecyclerViewPresenter) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RecyclerViewPresenter-green.svg?style=true)](https://android-arsenal.com/details/1/3593) [![appetize.io](https://img.shields.io/badge/appetize.io-Live%20Demo-blue.svg)](https://appetize.io/app/twkuv0xydcy5h8whmkcmx81kur) [![Javadoc](https://img.shields.io/badge/javadoc-SNAPSHOT-green.svg)](https://jitpack.io/com/github/kibotu/RecyclerViewPresenter/master-SNAPSHOT/javadoc/index.html) [![Build Status](https://travis-ci.org/kibotu/RecyclerViewPresenter.svg)](https://travis-ci.org/kibotu/RecyclerViewPresenter)  [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15) [![Gradle Version](https://img.shields.io/badge/gradle-5.3.1-green.svg)](https://docs.gradle.org/current/release-notes)  [![Kotlin](https://img.shields.io/badge/kotlin-1.3.30-green.svg)](https://kotlinlang.org/) [![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/kibotu/RecyclerViewPresenter/master/LICENSE) [![androidx](https://img.shields.io/badge/androidx-brightgreen.svg)](https://developer.android.com/topic/libraries/support-library/refactor)

Convenience library to handle different view types with different presenters in a single RecyclerView. 

[![Screenshot](https://raw.githubusercontent.com/kibotu/RecyclerViewPresenter/master/screenshot.png)](https://raw.githubusercontent.com/kibotu/RecyclerViewPresenter/master/screenshot.png)
  
### How to install
	
	repositories {
	    maven {
	        url "https://jitpack.io"
	    }
	}
		
	dependencies {
        implementation 'com.github.kibotu:RecyclerViewPresenter:-SNAPSHOT'
    }
    
### How to use

1. [Add the PresenterAdapter to your RecyclerView](app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/kotlin/PresenterActivity.kt#L22-L24)

        val adapter = PresenterAdapter<RecyclerViewModel<String>>()
        list.adapter = adapter
        
2. [Add a model with a Presenter as representation](app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/kotlin/PresenterActivity.kt#L34-L37) to the adapter, e.g.:

        adapter.append(RecyclerViewModel(myModelObject), PhotoPresenter::class.java)
        adapter.append(RecyclerViewModel(myModelObject), LabelPresenter::class.java)
        
3. Create a presenter, e.g. [PhotoPresenter](app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/kotlin/PhotoPresenter.kt#L14-L24) or [LabelPresenter](app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/kotlin/LabelPresenter.kt#L12-L19)

        class PhotoPresenter : Presenter<RecyclerViewModel<String>>() {

            override val layout: Int = R.layout.photo_presenter_item

            override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: RecyclerViewModel<String>, position: Int): Unit = with(viewHolder.itemView) {

            }
        }
        
4. Add click listener [to adapter](app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/MainActivity.java#L40) and [to presenter](app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/PhotoPresenter.java#L54-L59)

        adapter.onItemClick { item, view, position ->
            toast("$position. ${item.model}")
        }


4.1 or pass [to your RecyclerViewModel](app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/kotlin/PresenterActivity.kt#L39-L45)

        RecyclerViewModel(
            model = createRandomImageUrl(),
            onItemClickListener = { item, view, position ->
                toast("$position. $item")
            }
        )

### [Updating item](https://github.com/kibotu/RecyclerViewPresenter/blob/master/app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/kotlin/PresenterActivity.kt#L47)

     adapter.update(0, RecyclerViewModel(myNewModel))
       
### License
<pre>
Copyright 2019 Jan Rabe

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