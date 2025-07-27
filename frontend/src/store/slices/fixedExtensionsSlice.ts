import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit'
import { FixedExtension } from '@/types'
import {
  getFixedExtensions,
  toggleFixedExtension,
} from '@/services/extensionService'

interface FixedExtensionsState {
  list: FixedExtension[]
  loading: boolean
  error: string | null
}

const initialState: FixedExtensionsState = {
  list: [],
  loading: false,
  error: null,
}

// 고정 확장자 전체 조회
export const fetchFixedExtensions = createAsyncThunk(
  'fixedExtensions/fetchAll',
  async () => {
    return await getFixedExtensions()
  }
)

// 정 확장자 토글 (응답 없음, 성공만 확인)
export const toggleFixed = createAsyncThunk<void, number>(
  'fixedExtensions/toggle',
  async (id: number) => {
    await toggleFixedExtension(id)
  }
)

const fixedExtensionsSlice = createSlice({
  name: 'fixedExtensions',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchFixedExtensions.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(
        fetchFixedExtensions.fulfilled,
        (state, action: PayloadAction<FixedExtension[]>) => {
          state.list = action.payload
          state.loading = false
        }
      )
      .addCase(fetchFixedExtensions.rejected, (state, action) => {
        state.loading = false
        state.error = action.error.message ?? '에러 발생'
      })
      .addCase(toggleFixed.rejected, (state, action) => {
        state.error = action.error.message ?? '토글 실패'
      })
  },
})

export default fixedExtensionsSlice.reducer
