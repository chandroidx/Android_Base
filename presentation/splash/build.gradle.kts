plugins {
  androidLibrary()
  kotlinAndroid()
  kotlinKapt()
  hilt()
  jUnit5()
}

android {
  setLibraryConfig()
  setDataBindingEnabled()
  setProductFlavors(project::property)
}

dependencies {
  basePresentation()

  configureTestImplements()
}