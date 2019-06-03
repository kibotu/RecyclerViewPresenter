[![Donation](https://img.shields.io/badge/buy%20me%20a%20coffee-brightgreen.svg)](https://www.paypal.me/janrabe/5) [![About Jan Rabe](https://img.shields.io/badge/about-me-green.svg)](https://about.me/janrabe)
# RecyclerView Presenter [![](https://jitpack.io/v/kibotu/RecyclerViewPresenter.svg)](https://jitpack.io/#kibotu/RecyclerViewPresenter) [![](https://jitpack.io/v/kibotu/RecyclerViewPresenter/month.svg)](https://jitpack.io/#kibotu/RecyclerViewPresenter) [![Hits-of-Code](https://hitsofcode.com/github/kibotu/RecyclerViewPresenter)](https://hitsofcode.com/view/github/kibotu/RecyclerViewPresenter) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RecyclerViewPresenter-green.svg?style=true)](https://android-arsenal.com/details/1/3593) [![appetize.io](https://img.shields.io/badge/appetize.io-Live%20Demo-blue.svg)](https://appetize.io/app/twkuv0xydcy5h8whmkcmx81kur) [![Javadoc](https://img.shields.io/badge/javadoc-SNAPSHOT-green.svg)](https://jitpack.io/com/github/kibotu/RecyclerViewPresenter/master-SNAPSHOT/javadoc/index.html) [![Build Status](https://travis-ci.org/kibotu/RecyclerViewPresenter.svg)](https://travis-ci.org/kibotu/RecyclerViewPresenter)  [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15) [![Gradle Version](https://img.shields.io/badge/gradle-5.4.1-green.svg)](https://docs.gradle.org/current/release-notes)  [![Kotlin](https://img.shields.io/badge/kotlin-1.3.1-green.svg)](https://kotlinlang.org/) [![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/kibotu/RecyclerViewPresenter/master/LICENSE) [![androidx](https://img.shields.io/badge/androidx-brightgreen.svg)](https://developer.android.com/topic/libraries/support-library/refactor)

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


1. Create a presenter, e.g. [PhotoPresenter](app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/kotlin/PhotoPresenter.kt#L14-L24) or [LabelPresenter](app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/kotlin/LabelPresenter.kt#L12-L19)

        class LabelPresenter : Presenter<String>() {

            override val layout = R.layout.label_presenter_item

            override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: PresenterModel<String>, position: Int, payloads: MutableList<Any>?, adapter: Adapter) = with(viewHolder.itemView) {
                label.text = item.model
            }
        }

2. [Add the PresenterAdapter to your RecyclerView](app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/kotlin/PresenterActivity.kt#L23-L29)

        val adapter = PresenterAdapter()
        list.adapter = adapter

3. [Register Presenter](app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/kotlin/PresenterActivity.kt#L24-L26)

        adapter.registerPresenter(PhotoPresenter())
        adapter.registerPresenter(LabelPresenter())
        adapter.registerPresenter(NumberPresenter())
        
4. [Submit list of models with presenter matching layout](app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/kotlin/PresenterActivity.kt#L39-L51) to the adapter, e.g.:

        val items = ArrayList<PresenterModel<String>>()

        for (i in 0..99) {
            items.add(PresenterModel(createRandomImageUrl(), R.layout.photo_presenter_item))
            items.add(PresenterModel(createRandomImageUrl(), R.layout.label_presenter_item))
            items.add(PresenterModel(createRandomImageUrl(), R.layout.number_presenter_item))
        }

        adapter.submitList(items)

5. Add click listener [to adapter](app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/kotlin/PresenterActivity.kt#L31-L33)

        adapter.onItemClick { item, view, position ->
            snack("$position. ${item.model}")
        }

4.1 or pass [to your RecyclerViewModel](app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/kotlin/PresenterActivity.kt#L43-L45)

        val item = PresenterModel(createRandomImageUrl()) {
            snack("$position. ${item.model}")
        }

### [Updating item](app/src/main/java/net/kibotu/android/recyclerviewpresenter/app/kotlin/PresenterActivity.kt#L55)

     adapter.submitList(newItems)
       
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
