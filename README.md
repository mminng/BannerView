# BannerView
A BannerView for Android.
> Extends from RecyclerView.  
Show image or text.  
Simple to use and easy to expand.
# Getting Started
...
# Example
### add BannerView to your layout
> ImageBanner
```xml
<com.github.mminng.banner.BannerView
    ... />
```
> TextBanner
```xml
<com.github.mminng.banner.TextBannerView
    ... />
```
### BannerView
> Simple
```Kotlin
//create data (String type only)
val simpleData: List<String> = listOf(...)
//create DefaultBannerAdapter(already contains in BannerView)
val defaultAdapter: DefaultBannerAdapter = object : DefaultBannerAdapter() {
        override fun onBind(data: String, view: ImageView) {
            //load image
        }
    }
//set data
defaultAdapter.updateData(simpleData)
//item click
defaultAdapter.setOnItemClickListener { data, position ->
    //do something...
}
//set adapter for BannerView
bannerView.setAdapter(defaultAdapter)
//play
bannerView.play()
```
> Custom
```kotlin
//create data
val customData: List<CustomType> = listOf(...)
//class of custom adapter extends from BannerAdapter
class CustomAdapter : BannerAdapter<CustomType>(R.layout.custom_layout) {
    override fun onBindViewHolder(holder: BannerViewHolder, item: CustomType, position: Int) {
        //do your work
        //like holder.findViewById()
    }
}
//create CustomAdapter
val customAdapter = CustomAdapter()
//set data
customAdapter.updateData(customData)
//item click
customAdapter.setOnItemClickListener { data, position ->
    //do something...
}
//set adapter for BannerView
bannerView.setAdapter(customAdapter)
//play
bannerView.play()
```
### TextBannerView
> Simple
```kotlin
//create DefaultTextBannerAdapter(already contains in BannerView)
//String type only
val defaultAdapter = DefaultTextBannerAdapter()
...
//same as (Image)BannerView
```
> Custom
```kotlin
//same as (Image)BannerView
```
### Lifecycle
```kotlin
//addObserver
lifecycle.addObserver(bannerView)
//or
override fun onResume() {
    ...
    bannerView.resume()
}
override fun onPause() {
    ...
    bannerView.stop()
}
```
# Attrs
| Attrs | Description |
| ------| ----------- |
| app:bannerPadding | padding left and right |

# License
```markdown
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
