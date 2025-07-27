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

// ✅ 고정 확장자 전체 조회
export const fetchFixedExtensions = createAsyncThunk(
  'fixedExtensions/fetchAll',
  async () => {
    return await getFixedExtensions()
  }
)

// ✅ 고정 확장자 체크 상태 토글
export const toggleFixed = createAsyncThunk(
  'fixedExtensions/toggle',
  async (id: number) => {
    const result = await toggleFixedExtension(id)
    if (!result) throw new Error('응답이 null입니다')
    return result
  }
)

const fixedExtensionsSlice = createSlice({
  name: 'fixedExtensions',
  initialState,
  reducers: {},
  extraReducers: builder => {
    builder
      .addCase(fetchFixedExtensions.pending, state => {
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
      .addCase(toggleFixed.fulfilled, (state, action: PayloadAction<number>) => {
        const id = action.payload
        const index = state.list.findIndex(item => item.id === id)
        if (index !== -1) {
          state.list[index].checked = !state.list[index].checked
        }
      })
  },
})

export default fixedExtensionsSlice.reducer
