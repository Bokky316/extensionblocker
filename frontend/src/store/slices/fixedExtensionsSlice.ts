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

export const fetchFixedExtensions = createAsyncThunk(
  'fixedExtensions/fetchAll',
  async () => {
    return await getFixedExtensions()
  }
)

<<<<<<< HEAD
=======
// ✅ 고정 확장자 체크 상태 토글
>>>>>>> origin/master
export const toggleFixed = createAsyncThunk<FixedExtension, number>(
  'fixedExtensions/toggle',
  async (id: number) => {
    const result = await toggleFixedExtension(id)
    if (!result) throw new Error('응답이 null입니다')
<<<<<<< HEAD
    return result
=======
    return result  // ✅ result는 FixedExtension 타입이어야 함
>>>>>>> origin/master
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
      .addCase(fetchFixedExtensions.fulfilled, (state, action: PayloadAction<FixedExtension[]>) => {
        state.list = action.payload
        state.loading = false
      })
      .addCase(fetchFixedExtensions.rejected, (state, action) => {
        state.loading = false
        state.error = action.error.message ?? '에러 발생'
      })
      .addCase(toggleFixed.fulfilled, (state, action: PayloadAction<FixedExtension>) => {
        const updated = action.payload
        const index = state.list.findIndex(item => item.id === updated.id)
        if (index !== -1) {
          state.list[index].checked = updated.checked
        }
      })
      .addCase(toggleFixed.rejected, (state, action) => {
        state.error = action.error.message ?? '토글 실패'
      })
  },
})

export default fixedExtensionsSlice.reducer
