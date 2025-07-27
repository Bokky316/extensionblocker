import { useDispatch, useSelector } from 'react-redux'
import { useEffect } from 'react'
import {
  fetchFixedExtensions,
  toggleFixed,
} from '@/store/slices/fixedExtensionsSlice'
import type { RootState, AppDispatch } from '@/store'

export const useFixedExtensions = () => {
  const dispatch = useDispatch<AppDispatch>()

  // ✅ 각 필드를 개별적으로 추적
  const fixedList = useSelector((state: RootState) => state.fixedExtensions.list)
  const loading = useSelector((state: RootState) => state.fixedExtensions.loading)
  const error = useSelector((state: RootState) => state.fixedExtensions.error)

  useEffect(() => {
    dispatch(fetchFixedExtensions())
  }, [dispatch])

  const toggle = (id: number) => {
    dispatch(toggleFixed(id))
  }

  return {
    fixedList,
    loading,
    error,
    toggle,
  }
}
