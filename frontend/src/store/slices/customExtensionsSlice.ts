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

export const fetchCustomExtensionsThunk = createAsyncThunk(
  'customExtensions/fetchAll',
  async () => {
    return await getCustomExtensions()
  }
)

export const createCustomExtensionThunk = createAsyncThunk<
  CustomExtension,
  string,
  { rejectValue: { code: number; message: string } }
>('customExtensions/create', async (name, { rejectWithValue }) => {
  try {
    return await addCustomExtension(name)
  } catch (err: any) {
    if (err?.response?.data) {
      return rejectWithValue(err.response.data)
    }
    return rejectWithValue({ code: 5000, message: '알 수 없는 오류' })
  }
})

export const removeCustomExtensionThunk = createAsyncThunk<number, number>(
  'customExtensions/remove',
  async (id) => {
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
      .addCase(fetchCustomExtensionsThunk.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(fetchCustomExtensionsThunk.fulfilled, (state, action) => {
        state.loading = false
        state.list = action.payload ?? []
      })
      .addCase(fetchCustomExtensionsThunk.rejected, (state, action) => {
        state.loading = false
        state.error = action.error.message ?? '에러 발생'
      })
      .addCase(createCustomExtensionThunk.fulfilled, (state, action) => {
        state.list = [action.payload, ...state.list]
      })
      .addCase(createCustomExtensionThunk.rejected, (state, action) => {
        state.error = action.payload?.message ?? '등록 실패'
      })
      .addCase(removeCustomExtensionThunk.fulfilled, (state, action) => {
        state.list = state.list.filter((e) => e.id !== action.payload)
      })
  },
})

export default customExtensionsSlice.reducer