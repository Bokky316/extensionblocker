import { useCallback, useEffect } from 'react'
import { useAppDispatch } from '@/hooks/useAppDispatch'
import { useAppSelector } from '@/hooks/useAppSelector'
import type { RootState } from '@/store'
import {
  fetchFixedExtensions,
  toggleFixed,
} from '@/store/slices/fixedExtensionsSlice'

export const useFixedExtensions = () => {
  const dispatch = useAppDispatch()

  const fixedList = useAppSelector((state: RootState) => state.fixedExtensions.list)
  const loading = useAppSelector((state: RootState) => state.fixedExtensions.loading)
  const error = useAppSelector((state: RootState) => state.fixedExtensions.error)

  const fetch = useCallback(() => {
    dispatch(fetchFixedExtensions())
  }, [dispatch])

  // toggle 성공 후 전체 fetch
  const toggle = useCallback(
    async (id: number) => {
      try {
        await dispatch(toggleFixed(id)).unwrap()
        await dispatch(fetchFixedExtensions())
      } catch (err) {
        console.error('토글 실패:', err)
      }
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
