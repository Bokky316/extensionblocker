import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import {
  getCustomExtensions,
  addCustomExtension,
  deleteCustomExtension,
} from '../services/extensionService'
import type { CustomExtension } from '../types'

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
  'customExtension/fetchAll',
  async (_, thunkAPI) => {
    return await getCustomExtensions()
  }
)

export const createCustomExtension = createAsyncThunk(
  'customExtension/create',
  async (name: string, thunkAPI) => {
    return await addCustomExtension(name)
  }
)

export const removeCustomExtension = createAsyncThunk(
  'customExtension/remove',
  async (id: number, thunkAPI) => {
    await deleteCustomExtension(id)
    return id
  }
)

const customExtensionSlice = createSlice({
  name: 'customExtension',
  initialState,
  reducers: {},
  extraReducers: builder => {
    builder
      .addCase(fetchCustomExtensions.pending, state => {
        state.loading = true
        state.error = null
      })
      .addCase(fetchCustomExtensions.fulfilled, (state, action) => {
        state.loading = false
        state.list = action.payload
      })
      .addCase(fetchCustomExtensions.rejected, (state, action) => {
        state.loading = false
        state.error = '조회 실패'
      })

      .addCase(createCustomExtension.fulfilled, (state, action) => {
        state.list.unshift(action.payload)
      })
      .addCase(createCustomExtension.rejected, (state, action) => {
        state.error = '추가 실패'
      })

      .addCase(removeCustomExtension.fulfilled, (state, action) => {
        state.list = state.list.filter(item => item.id !== action.payload)
      })
      .addCase(removeCustomExtension.rejected, (state, action) => {
        state.error = '삭제 실패'
      })
  },
})

export default customExtensionSlice.reducer
