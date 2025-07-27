import { configureStore } from '@reduxjs/toolkit'
import fixedExtensionsReducer from '@/store/slices/fixedExtensionsSlice'
import customExtensionsReducer from '@/store/slices/customExtensionsSlice'

export const store = configureStore({
  reducer: {
    fixedExtensions: fixedExtensionsReducer,
    customExtensions: customExtensionsReducer,
  },
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch