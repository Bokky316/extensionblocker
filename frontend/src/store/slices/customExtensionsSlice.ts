import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import type { CustomExtension } from '@/types'
import {
  getCustomExtensions,
  addCustomExtension,
  deleteCustomExtension,
} from '@/services/extensionService'

interface CustomExtensionState {
  list: CustomExtension[]
  loading: boolean
  error: string | null
}

const initialState: CustomExtensionState = {
  list: [],
  loading: false,
  error: null,
}

export const fetchCustomExtensions = createAsyncThunk(
  'customExtensions/fetchAll',
  async () => await getCustomExtensions()
)

export const createCustomExtension = createAsyncThunk(
  'customExtensions/create',
  async (name: string) => await addCustomExtension(name)
)

export const removeCustomExtension = createAsyncThunk(
  'customExtensions/remove',
  async (id: number) => {
    await deleteCustomExtension(id)
    return id
  }
)

const customExtensionsSlice = createSlice({
  name: 'customExtensions',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchCustomExtensions.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(fetchCustomExtensions.fulfilled, (state, action) => {
        state.loading = false
        state.list = action.payload
      })
      .addCase(fetchCustomExtensions.rejected, (state, action) => {
        state.loading = false
        state.error = action.error.message ?? '에러 발생'
      })
      .addCase(createCustomExtension.fulfilled, (state, action) => {
        state.list = [action.payload, ...state.list]
      })
      .addCase(removeCustomExtension.fulfilled, (state, action) => {
        state.list = state.list.filter((e) => e.id !== action.payload)
      })
  },
})

export default customExtensionsSlice.reducer
