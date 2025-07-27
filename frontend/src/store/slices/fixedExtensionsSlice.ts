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

// 고정 확장자 토글 (nullable 허용)
export const toggleFixed = createAsyncThunk<FixedExtension | null, number>(
  'fixedExtensions/toggle',
  async (id: number) => {
    const result = await toggleFixedExtension(id)
    return result // null도 허용
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
      .addCase(toggleFixed.fulfilled, (state, action: PayloadAction<FixedExtension | null>) => {
        const toggled = action.payload
        if (!toggled) return

        const target = state.list.find(item => item?.id === toggled.id)
        if (target) {
          target.checked = toggled.checked
        }
      })
      .addCase(toggleFixed.rejected, (state, action) => {
        state.error = action.error.message ?? '토글 실패'
      })
  },
})

export default fixedExtensionsSlice.reducer
