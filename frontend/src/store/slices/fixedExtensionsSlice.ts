import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { FixedExtension } from '@/types'
import {
  getFixedExtensions,
  toggleFixedExtension,
} from '@/services/extensionService'

export const fetchFixedExtensionsThunk = createAsyncThunk(
  'fixedExtensions/fetchAll',
  async (): Promise<FixedExtension[]> => {
    return await getFixedExtensions()
  }
)

export const toggleFixedExtensionThunk = createAsyncThunk(
  'fixedExtensions/toggle',
  async (id: number): Promise<FixedExtension> => {
    return await toggleFixedExtension(id)
  }
)

type FixedExtensionsState = {
  list: FixedExtension[]
  loading: boolean
  error: string | null
}

const initialState: FixedExtensionsState = {
  list: [],
  loading: false,
  error: null,
}

const fixedExtensionsSlice = createSlice({
  name: 'fixedExtensions',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchFixedExtensionsThunk.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(fetchFixedExtensionsThunk.fulfilled, (state, action) => {
        state.loading = false
        state.list = action.payload
      })
      .addCase(fetchFixedExtensionsThunk.rejected, (state, action) => {
        state.loading = false
        state.error = action.error.message ?? '고정 확장자 조회 실패'
      })
      .addCase(toggleFixedExtensionThunk.fulfilled, (state, action) => {
        const updated = action.payload
        const index = state.list.findIndex((item) => item.id === updated.id)
        if (index !== -1) {
          state.list[index] = updated
        }
      })
      .addCase(toggleFixedExtensionThunk.rejected, (state, action) => {
        state.error = action.error.message ?? '고정 확장자 토글 실패'
      })
  },
})

export default fixedExtensionsSlice.reducer
