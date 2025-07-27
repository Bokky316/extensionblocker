import { useCallback, useEffect } from 'react'
import { useAppDispatch } from '@/hooks/useAppDispatch'
import { useAppSelector } from '@/hooks/useAppSelector'
import {
  fetchFixedExtensions,
  toggleFixed,
} from '@/store/slices/fixedExtensionsSlice'

export const useFixedExtensions = () => {
  const dispatch = useAppDispatch()

  const fixedList = useAppSelector((state) => state.fixedExtensions.list)
  const loading = useAppSelector((state) => state.fixedExtensions.loading)
  const error = useAppSelector((state) => state.fixedExtensions.error)

  const fetch = useCallback(() => {
    dispatch(fetchFixedExtensions())
  }, [dispatch])

  const toggle = useCallback(
    (id: number) => {
      dispatch(toggleFixed(id))
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
