import { useCallback, useEffect } from 'react'
import { useAppDispatch } from '@/hooks/useAppDispatch'
import { useAppSelector } from '@/hooks/useAppSelector'
import type { RootState } from '@/store'
import {
  fetchFixedExtensionsThunk,
  toggleFixedExtensionThunk,
} from '@/store/slices/fixedExtensionsSlice'

export const useFixedExtensions = () => {
  const dispatch = useAppDispatch()

  const fixedList = useAppSelector(
    (state: RootState) => state.fixedExtensions?.list ?? []
  )
  const loading = useAppSelector(
    (state: RootState) => state.fixedExtensions?.loading ?? false
  )
  const error = useAppSelector(
    (state: RootState) => state.fixedExtensions?.error ?? null
  )

  const fetch = useCallback(() => {
    dispatch(fetchFixedExtensionsThunk())
  }, [dispatch])

  const toggle = useCallback(
    async (id: number) => {
      return await dispatch(toggleFixedExtensionThunk(id)).unwrap()
    },
    [dispatch]
  )

  useEffect(() => {
    fetch()
  }, [fetch])

  return {
    fixedList,
    loading,
    error,
    toggle,
  }
}