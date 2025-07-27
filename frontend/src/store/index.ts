import { configureStore } from '@reduxjs/toolkit'
import customExtensionReducer from './customExtension'

export const store = configureStore({
  reducer: {
    customExtension: customExtensionReducer,
  },
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch
