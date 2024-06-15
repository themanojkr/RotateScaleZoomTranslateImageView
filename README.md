# MatrixGestureDetectors Library

MatrixGestureDetectors is an Android library that provides gesture detection capabilities for image views, enabling smooth drag, scale, rotate, and zoom functionalities.

## Introduction

MatrixGestureDetectors is designed to enhance the user experience by allowing developers to easily integrate gesture detection and manipulation capabilities into their Android applications. This library includes a custom ImageView class that supports dragging, scaling, rotating, and zooming.

## Features

- Drag to move the image
- Pinch to zoom in and out
- Rotate the image with two fingers
- Smooth and responsive gestures

![MatrixGestureDetectors in action]([path/to/your/image.gi](https://github.com/manoj7755/RotateScaleZoomTranslateImageView/assets/88922867/444f02cd-ee1c-4b6f-b842-9257af1e9ec7)f)


## Installation

To add MatrixGestureDetectors to your project, include the following dependency in your `build.gradle` file:

```gradle
dependencies {
    implementation 'com.github.manoj7755:RotateScaleZoomTranslateImageView:1.0.0'
}

```
## Usage
XML Layout
To use the RotateScaleZoomTranslateImageView, add it to your XML layout file:
````
<com.manoj.matrixgesturedetectors.RotateScaleZoomTranslateImageView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:src="@drawable/your_image" />
