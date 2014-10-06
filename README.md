# Parallax ImageView Android library

![alt tag](https://raw.githubusercontent.com/irimiaionut/ParallaxImageView/master/screen.png)

**1. Project**

   You have here a library that lets you use a imageview to simulate a 3D effect like on Amazon FirePhone and Iphone. To do so, the imageview is set with a margin and based on the phone's movement and interpreted with the Sensor.TYPE_ROTATION_VECTOR moves the margin so that it creates a 3D in depht effect.


**2. Recommended usage**

   This can be used on any layout but keep in mind to check how it affects the overall behavior of the app(better no animations than interrupted ones).
   My recommendation would be to use it on light implementations like loginscreens / settings.


**3. Recommended implementation**
```java
<RelativeLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:layout_margin="0dip"
    tools:context="com.example.sensortest.MainActivity" >
    
    <com.android.irimiaionut.parallaxImageView.ParallaxImageView
        android:id="@+id/backgr"
        android:src="@drawable/backgr"
        android:layout_height="fill_parent"
        android:layout_width="fill_parent"
        android:scaleType="centerCrop"
        android:adjustViewBounds="true"/>
</RelativeLayout>
```

```java
backgr = (ParallaxImageView) findViewById(R.id.backgr);
backgr.setMargins(300, 200);
backgr.setMultipliers(1.5f, 1.7f);
```




**4. Notes**

* Solved some Galaxy devices exception for event.values on ```getRotationMatrixFromVector();```
* Min SDK 11



