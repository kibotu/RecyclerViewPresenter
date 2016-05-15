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
        compile 'com.github.kibotu:android-utils:1.0.0'
    }
    
### How to use

1. Create a presenter (If your ViewHolder inherits from BaseViewHolder it automatically cares for your ButterKnife bindings/unbindings)
 
        public class PhotoPresenter extends Presenter<String, PhotoPresenter.ViewHolder> {
        
            public PhotoPresenter(@NonNull PresenterAdapter<String> presenterAdapter) {
                super(presenterAdapter);
            }
        
            @LayoutRes
            @Override
            protected int getLayout() {
                return R.layout.photo_presenter_item; // your layout
            }
        
            /**
             * Hint: Required method; if java had a way of allocating a 
             * generic I could move this method into the adapter. :(
             */ 
            @NonNull
            @Override
            protected ViewHolder createViewHolder(@LayoutRes int layout, @NonNull ViewGroup parent) {
                return new ViewHolder(layout, parent);
            }
        
            /**
             * Binding your model against your ViewHolder.
            @Override
            public void bindViewHolder(@NonNull ViewHolder viewHolder, @NonNull String item, int position) {
                       
                Glide.with(viewHolder.itemView.getContext()).load(item).into(viewHolder.photo);
                
                viewHolder.itemView.setOnClickListener(v -> {
                    
                    // accessing the click listener from the adapter:
                    if (presenterAdapter.getOnItemClickListener() != null)
                        presenterAdapter.getOnItemClickListener().onItemClick(item, v, position);
                });
            }
        
            public static class ViewHolder extends BaseViewHolder {
        
                @NonNull
                @BindView(R.id.photo)
                ImageView photo;
        
                public ViewHolder(@LayoutRes int layout, @NonNull ViewGroup parent) {
                    super(layout, parent); // does butterknife binding
                }
            }
        }


1. Add the PresenterAdapter to your RecyclerView

        PresenterAdapter<String> adapter = new PresenterAdapter<>();
        list.setAdapter(adapter);
        
2. Add Presenter to the adapter, e.g.:

        adapter.add(myModelObject, PhotoPresenter.class);
        adapter.add(myModelObject, LabelPresenter.class);
        

       
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