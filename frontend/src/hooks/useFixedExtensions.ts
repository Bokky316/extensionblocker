import { useDispatch, useSelector } from 'react-redux'
import { useEffect } from 'react'
import {
  fetchFixedExtensionsThunk,
  toggleFixedExtensionThunk,
} from '@/store/slices/fixedExtensionsSlice'
import type { RootState, AppDispatch } from '@/store'

export const useFixedExtensions = () => {
  const dispatch = useDispatch<AppDispatch>()

  const fixedList = useSelector((state: RootState) => state.fixedExtensions.list ?? [])
  const loading = useSelector((state: RootState) => state.fixedExtensions.loading)
  const error = useSelector((state: RootState) => state.fixedExtensions.error)

  useEffect(() => {
    dispatch(fetchFixedExtensionsThunk())
  }, [dispatch])

  const toggle = (id: number) => {
    dispatch(toggleFixedExtensionThunk(id))
  }

  return {
    fixedList,
    loading,
    error,
    toggle,
  }
}